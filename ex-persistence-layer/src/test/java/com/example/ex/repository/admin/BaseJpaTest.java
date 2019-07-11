package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.entity.admin.BaseEntity;

/**
 * This class includes all the common operations for the JPA repositories In
 * order to re use code, each repository should extend from here.
 * 
 * Implementors must specify the repository being tested and some sample
 * entities The base class will automatically test CRUD operations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public abstract class BaseJpaTest<T extends BaseEntity, I> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJpaTest.class);
    
    public static final String INTEGRATION_TESTS_PROFILE = "integration-tests";
    public static final String JPA_PROFILE = "jpa";

    @Autowired
    protected TestEntityManager entityManager;

    protected JpaRepository<T, Integer> jpaRepository;

    protected abstract JpaRepository<T, Integer> getImplementationRepository();

    protected abstract T getNewTestEntity();

    protected abstract void updateEntity(T entity);

    @Before
    public void before() {
        jpaRepository = getImplementationRepository();
        getDependencies().forEach(entity -> entityManager.persist(entity));
        entityManager.flush();
    }
    
    public List<BaseEntity> getDependencies() {
        return Collections.emptyList();
    }

    /*
     * Tests Create method
     */
    @Test
    public void givenEntity_whenSave_thenGetSameEntity() {
        T entity = getNewTestEntity();
        assertThat(entity.getId()).isNull();
        jpaRepository.save(entity);
        jpaRepository.flush();

        BaseEntity foundEntity = entityManager.find(entity.getClass(), entity.getId());
        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getId()).isNotNull();
        assertThat(foundEntity).isEqualTo(entity);
    }

    /*
     * Tests Read method
     */
    @Test
    public void whenFindById_thenReturnEntity() {
        T entity = getNewTestEntity();

        entityManager.persist(entity);
        entityManager.flush();

        Optional<T> found = jpaRepository.findById(entity.getId());
        T newEntity = found.orElse(null);

        assertThat(newEntity).isNotNull();
        assertThat(newEntity).isEqualTo(entity);
    }

    /*
     * Tests Update method
     */
    @Test
    public void givenEntity_whenUpdate_thenGetUpdatedEntity() {
        T entity = getNewTestEntity();
        jpaRepository.save(entity);
        jpaRepository.flush();

        assertThat(entity.getId()).isNotNull();

        updateEntity(entity);
        jpaRepository.save(entity);
        jpaRepository.flush();

        BaseEntity newEntity = entityManager.find(entity.getClass(), entity.getId());

        assertThat(newEntity).isNotNull();
        assertThat(newEntity).isEqualTo(entity);
    }

    /*
     * Tests Delete method
     */
    @Test
    public void givenEntity_whenDelete_thenGetOk() {
        T entity = getNewTestEntity();
        jpaRepository.save(entity);
        jpaRepository.flush();

        BaseEntity saved = entityManager.find(entity.getClass(), entity.getId());
        assertThat(saved).isNotNull();
        assertThat(saved).isEqualTo(entity);

        jpaRepository.delete(entity);
        jpaRepository.flush();

        BaseEntity notFound = entityManager.find(saved.getClass(), saved.getId());
        assertThat(notFound).isNull();
    }

    /**
     *  For each field on the tested class, this method looks checks if the field has 
     *  the annotation @Column(nullable=false)
     *  If so then sets the field to null and tries to save the entity to the database.
     *  then, it excepts to fail and asserts that there is a ConstraintViolationException 
     */
    @Test
    public void shouldNotSaveNonNullAttributes() throws IllegalArgumentException, IllegalAccessException {
        T entity = getNewTestEntity();
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Column c = field.getAnnotation(Column.class);
            ManyToOne mto = field.getAnnotation(ManyToOne.class);
            if (c != null && !c.nullable() || (mto != null && !mto.optional())) {
                field.setAccessible(true);
                field.set(entity, null);
                try {
                    LOGGER.info("Should not save entity {}, because field \"{}\" was set to null",entity,field.getName());
                    jpaRepository.save(entity);
                    fail("Entity " + entity + " with non null field " +  field.getName() + " was saved with null value");
                } catch (ConstraintViolationException exception) {
                    assertThat(exception).isNotNull();
                    LOGGER.info("Entity {} was successfully not saved, because field \"{}\" was set to null",entity,field.getName());
                }
            }
            entity = getNewTestEntity();
        }
    }
    
    /**
     *  For each field on the tested class, this method looks checks if the field has 
     *  the annotation @Column(unique=true) or @NotNull.
     *  If so then sets the field's value to match an already saved one and tries to save 
     *  the entity to the database. It excepts to fail and asserts that there is a ConstraintViolationException 
     * @throws InstantiationException 
     */
    @Test
    public void shouldNotSaveDuplicatedAttributes() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        T existingEntity = getNewTestEntity();
        jpaRepository.save(existingEntity);

        Class<?> clazz = existingEntity.getClass();

        @SuppressWarnings("unchecked")
        T newEntity = (T) clazz.newInstance();
        updateEntity(newEntity);

        for (Field field : clazz.getDeclaredFields()) {
            Column c = field.getAnnotation(Column.class);
            if (c != null && c.unique()) {
                field.setAccessible(true);
                Object value = field.get(existingEntity);
                field.set(newEntity,value);

                try {
                    LOGGER.info("Should not save entity {}, because field \"{}\" was already saved and is unique",newEntity,field.getName());
                    jpaRepository.save(newEntity);
                    fail("Entity " + newEntity + " with unique field " +  field.getName() + " was saved more than once");
                } catch (ConstraintViolationException | DataIntegrityViolationException exception) {
                    assertThat(exception).isNotNull();
                    LOGGER.info("Entity {} was successfully not saved, because field \"{}\" was already saved",newEntity,field.getName());
                }
            }
            newEntity = getNewTestEntity();
        }
    }
    
}