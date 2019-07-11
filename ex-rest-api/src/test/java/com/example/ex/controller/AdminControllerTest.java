package com.example.ex.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.example.ex.configuration.ControllerTestConfiguration;
import com.example.ex.exception.BusinessValidationException;
import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.interceptor.AuthInterceptor;
import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.service.BaseService;

@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
public abstract class AdminControllerTest<T extends BaseEntity> extends ControllerTestConfiguration {
    
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminControllerTest.class);
    
    @MockBean
    private AuthInterceptor authInterceptor;
    
    protected BaseService<T> service;

    protected SimpleDateFormat dateFormat;
    protected SimpleDateFormat timestampFormat;
    protected SimpleDateFormat timeFormat;

    private ObjectMapper mapper = new ObjectMapper();

    protected String baseUri;

    protected Class<T> clazz;

    public abstract List<T> getListOfEntities();

    public abstract T getNewEntity();

    public abstract void initController();

    @Before
    public final void initBaseController() throws Exception {
        initController();
        if (clazz == null || baseUri == null || service == null) {
            throw new RuntimeException(
                    "Please set implementation class, base uri and jpa repository to use by implementing method initController");
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        Mockito.when(authInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(authInterceptor.beforeHandshake(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
    }
    
    protected T getNewUpdateEntity() {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAllEntities() throws Exception {
        List<T> entities = getListOfEntities();
        Mockito.when(service.findAll()).thenReturn(entities);
        MvcResult result = this.mockMvc.perform(get(baseUri).accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andReturn();

        List<T> entitiesResult = fromJsonResultToList(result);
        assertThat(entities).containsExactlyInAnyOrder((T[]) entitiesResult.toArray(new BaseEntity[0]));
        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void whenGetOne_shouldReturnEntity() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));
        MvcResult result = this.mockMvc.perform(get(baseUri + "/" + baseEntity.getId()).accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        T entityResult = fromJsonResult(result);

        assertThat(entity).isEqualTo(entityResult);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }

    @Test
    public void whenGetOne_shouldReturnEntityNotFound() throws Exception {
        Mockito.when(firmService.findById(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get(baseUri + "/1").accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS)).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetOneWithInvalidId_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(get(baseUri + "/A").accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCreate_shouldReturnCreated() throws Exception {
        T entity = getNewEntity();
        this.shouldReturnCreated(entity);
    }

    @Test
    public void whenCreateWithId_shouldReturnBadRequest() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);
        byte[] json = toJson(entity);
        create(json).andExpect(status().isBadRequest()).andDo(print()).andReturn();
        Mockito.verify(service, Mockito.times(0)).save(entity);
    }
    
    protected void shouldReturnCreated(T entity) throws Exception {
        byte[] json = toJson(entity);

        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);
        Mockito.when(service.save(entity)).thenReturn(entity);

        MvcResult result = create(json).andExpect(status().isCreated())
                .andExpect(header().string("location", equalTo("http://localhost" + baseUri + "/" + baseEntity.getId())))
                .andDo(print()).andReturn();

        T entityResult = fromJsonResult(result);

        assertThat(entity).isEqualTo(entityResult);
        Mockito.verify(service, Mockito.times(1)).save(entity);
    }

    protected void shouldReturnUpdated(T entity) throws Exception {
        byte[] json = toJson(entity);

        BaseEntity baseEntity = (BaseEntity) entity;
        assertThat(baseEntity.getId()).isNotNull();
        Mockito.when(service.save(entity)).thenReturn(entity);

        update(json,baseEntity.getId()).andExpect(status().isNoContent()).andDo(print()).andReturn();

        Mockito.verify(service, Mockito.times(1)).save(entity);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }
    
    protected ResultActions create(byte[] json) throws Exception {
        LOGGER.debug("Create Request body {}", new String(json));
        return this.mockMvc.perform(post(baseUri).content(json).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS));
    }

    protected ResultActions update(byte[] json, Integer id) throws Exception {
        LOGGER.debug("Update Request body {}", new String(json));
        return this.mockMvc.perform(put(baseUri + "/" + id).content(json).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS));
    }

    protected ResultActions del(Integer id) throws Exception {
        return this.mockMvc.perform(delete(baseUri + "/" + id).header("Authorization", "Bearer " + JWTS));
    }
    
    protected void shouldReturnBadRequestWhenCreate(T entity, Matcher<? super String> matcher) throws Exception {
        byte[] json = toJson(entity);
        shouldReturnBadRequestWhenCreate(json,entity,matcher);
    }
    
    protected void shouldReturnBadRequestWhenCreate(byte[] json, T entity, Matcher<? super String> matcher) throws Exception {
        List<Matcher<? super String>> matchers = new ArrayList<>();
        matchers.add(matcher);
        shouldReturnBadRequestWhenCreate(json,entity,matchers);
    }
    
    protected void shouldReturnBadRequestWhenCreate(byte[] json, T entity, List<Matcher<? super String>> matchers) throws Exception {
        BaseEntity baseEntity = (BaseEntity) entity;
        assertThat(baseEntity.getId()).isNull();
        MvcResult result = create(json).andExpect(status().isBadRequest()).andDo(print()).andReturn();

        validateIfErrror(result,entity,matchers);

        Mockito.verify(service, Mockito.times(0)).save(entity);
    }

    private MvcResult shouldReturnConflictWhenCreate(T entity) throws Exception {
        byte[] json = toJson(entity);
        return create(json).andExpect(status().isConflict()).andDo(print()).andReturn();
    }

    private MvcResult shouldReturnNotFoundWhenCreate(T entity) throws Exception {
        byte[] json = toJson(entity);
        return create(json).andExpect(status().isNotFound()).andDo(print()).andReturn();
    }

    private MvcResult shouldReturnConflictWhenUpdate(T entity) throws Exception {
        byte[] json = toJson(entity);
        return update(json, ((BaseEntity)entity).getId()).andExpect(status().isConflict()).andDo(print()).andReturn();
    }

    protected void shouldReturnBadRequestWhenUpdate(T entity, Matcher<? super String> matcher) throws Exception {
        byte[] json = toJson(entity);
        shouldReturnBadRequestWhenUpdate(json,entity,matcher);
    }

    protected void shouldReturnBadRequestWhenUpdate(byte[] json, T entity, Matcher<? super String> matcher) throws Exception {
        List<Matcher<? super String>> matchers = new ArrayList<>();
        matchers.add(matcher);
        shouldReturnBadRequestWhenUpdate(json,entity,matchers);
    }
    
    protected void shouldReturnBadRequestWhenUpdate(byte[] json, T entity, List<Matcher<? super String>> matchers) throws Exception {
        BaseEntity baseEntity = (BaseEntity) entity;
        assertThat(baseEntity.getId()).isNotNull();
        MvcResult result = update(json, ((BaseEntity)entity).getId()).andExpect(status().isBadRequest()).andDo(print()).andReturn();

        validateIfErrror(result,entity, matchers);
        
        Mockito.verify(service, Mockito.times(0)).save(entity);
        Mockito.verify(service, Mockito.times(0)).findById(baseEntity.getId());
    }
    
    // generic error handling, check if changed
    private void validateIfErrror(MvcResult result, T entity,  List<Matcher<? super String>> matchers) throws NoSuchFieldException, SecurityException {
        Exception content = result.getResolvedException();
        if (MethodArgumentNotValidException.class.equals(content.getClass())) {
           MethodArgumentNotValidException exception = (MethodArgumentNotValidException) content;
           List<ObjectError> errors = exception.getBindingResult().getAllErrors();

           assertThat(errors.size()).isEqualTo(1);
           FieldError only = (FieldError) errors.get(0);

           Assert.assertThat(only.getCodes()[0], anyOf(matchers));

           String clazz = only.getObjectName();
           assertThat(entity.getClass().getSimpleName().toLowerCase()).isEqualTo(clazz.toLowerCase());

           Field field = entity.getClass().getDeclaredField(only.getField());   
           assertThat(field).isNotNull();
        } else if (HttpMessageNotReadableException.class.equals(content.getClass())) {
           JsonMappingException exception = (JsonMappingException) content.getCause();
           List<Reference> errors = exception.getPath();

           assertThat(errors.size()).isEqualTo(1);
           Reference ref = errors.get(0);

           Assert.assertThat(exception.getLocalizedMessage(), anyOf(matchers));

           String clazz = ref.getDescription();
           assertThat(clazz).contains(entity.getClass().getName());

           Field field = entity.getClass().getDeclaredField(ref.getFieldName());   
           assertThat(field).isNotNull();

        } else {
           fail("there should be an error but got: " + content);
        }
    }

    private MvcResult shouldReturnNotFoundWhenUpdate(T entity) throws Exception {
        byte[] json = toJson(entity);
        return update(json, ((BaseEntity)entity).getId()).andExpect(status().isNotFound()).andDo(print()).andReturn();
    }

    private MvcResult shouldReturnNotFoundWhenDelete(T entity) throws Exception {
        return del(((BaseEntity)entity).getId()).andExpect(status().isNotFound()).andDo(print()).andReturn();
    }

    private MvcResult shouldReturnConflictWhenDelete(T entity) throws Exception {
        return del(((BaseEntity)entity).getId()).andExpect(status().isConflict()).andDo(print()).andReturn();
    }
    
    protected byte[] jsonReplace(T entity, String attribute, String value) throws Exception {
        byte[] json = toJson(entity);
        JSONObject jsonObject = new JSONObject(new String(json));
        jsonObject.remove(attribute);
        jsonObject.put(attribute,value);
        return jsonObject.toString().getBytes();
    }
    
    @Test
    public void whenUpdate_shouldReturnNoContent() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);
        byte[] firmJson = toJson(entity);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));

        this.mockMvc
                .perform(put(baseUri + "/" + baseEntity.getId()).content(firmJson)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS))
                .andExpect(status().isNoContent()).andDo(print()).andReturn();

        Mockito.verify(service, Mockito.times(1)).save(entity);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }

    @Test
    public void whenUpdateNonExistingEntity_shouldReturnNoFound() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);
        byte[] firmJson = toJson(entity);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.empty());

        this.mockMvc
                .perform(put(baseUri + "/" + baseEntity.getId()).content(firmJson)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + JWTS))
                .andExpect(status().isNotFound()).andDo(print()).andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }

    @Test
    public void whenDelete_shouldReturnNoContent() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));

        this.mockMvc.perform(delete(baseUri + "/" + baseEntity.getId()).header("Authorization", "Bearer " + JWTS)).andExpect(status().isNoContent())
                .andDo(print()).andReturn();

        Mockito.verify(service, Mockito.times(1)).deleteById(baseEntity.getId());
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }

    @Test
    public void whenDeleteNonExistingEntity_shouldReturnNoFound() throws Exception {
        Integer id = 1;
        Mockito.when(service.findById(id)).thenReturn(Optional.empty());

        this.mockMvc.perform(delete(baseUri + "/" + id).header("Authorization", "Bearer " + JWTS)).andExpect(status().isNotFound()).andDo(print()).andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(id);
    }
    
    @Test
    public void shouldReturnConflictWhenCreateDuplicated() throws Exception {
        T entity = getNewEntity();

        Mockito.when(service.save(entity)).thenThrow(EntityAlreadyExistsException.class);
        
        Exception exception = shouldReturnConflictWhenCreate(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(EntityAlreadyExistsException.class);
        Mockito.verify(service, Mockito.times(1)).save(entity);
    }
    
    @Test
    public void shouldReturnNotFoundWhenFkNotFound() throws Exception {
        T entity = getNewEntity();

        Mockito.when(service.save(entity)).thenThrow(EntityNotFoundException.class);
        
        Exception exception = shouldReturnNotFoundWhenCreate(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(EntityNotFoundException.class);
        Mockito.verify(service, Mockito.times(1)).save(entity);
    }
    
    @Test
    public void shouldReturnConflictWhenUpdateDuplicated() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));
        Mockito.when(service.save(entity)).thenThrow(EntityAlreadyExistsException.class);
        
        Exception exception = shouldReturnConflictWhenUpdate(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(EntityAlreadyExistsException.class);
        Mockito.verify(service, Mockito.times(1)).save(entity);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }
    
    @Test
    public void shouldReturnNotFoundWhenUpdateWithFkNotFound() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));
        Mockito.when(service.save(entity)).thenThrow(EntityNotFoundException.class);
        
        Exception exception = shouldReturnNotFoundWhenUpdate(entity).getResolvedException();;

        assertThat(exception.getClass()).isEqualTo(EntityNotFoundException.class);
        Mockito.verify(service, Mockito.times(1)).save(entity);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }
    
    @Test
    public void shouldReturnNotFoundWhenDeleteEntityNotFound() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.doThrow(EntityNotFoundException.class).when(service).deleteById(baseEntity.getId());
        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));

        Exception exception = shouldReturnNotFoundWhenDelete(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(EntityNotFoundException.class);
        Mockito.verify(service, Mockito.times(1)).deleteById(baseEntity.getId());
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }
    
    @Test
    public void shouldReturnConflictWhenDeleteRelatedEntity() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.doThrow(EntityReferenceException.class).when(service).deleteById(baseEntity.getId());
        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.of(entity));

        Exception exception = shouldReturnConflictWhenDelete(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(EntityReferenceException.class);
        Mockito.verify(service, Mockito.times(1)).deleteById(baseEntity.getId());
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
    }
    
    @Test
    public void shouldReturnConflictWhenCreateWithBusinessError() throws Exception {
        T entity = getNewEntity();

        Mockito.when(service.save(entity)).thenThrow(BusinessValidationException.class);
        
        Exception exception = shouldReturnConflictWhenCreate(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(BusinessValidationException.class);
        Mockito.verify(service, Mockito.times(1)).save(entity);
    }
    
    @Test
    public void shouldReturnConflictWhenUpdateWithBusinessError() throws Exception {
        T entity = getNewEntity();
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setId(1);

        Mockito.when(service.findById(baseEntity.getId())).thenReturn(Optional.ofNullable(entity));
        Mockito.when(service.save(entity)).thenThrow(BusinessValidationException.class);
        
        Exception exception = shouldReturnConflictWhenUpdate(entity).getResolvedException();

        assertThat(exception.getClass()).isEqualTo(BusinessValidationException.class);
        Mockito.verify(service, Mockito.times(1)).findById(baseEntity.getId());
        Mockito.verify(service, Mockito.times(1)).save(entity);
    }
    
    /**
     * Convert JSON Result to object.
     * 
     * @param result The contents
     * @param tClass The expected object class
     * @return The result as class.
     * 
     * @throws Exception if you got any of the above wrong.
     */
    protected List<T> fromJsonResultToList(MvcResult result) {
        try {
            CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return this.mapper.readValue(result.getResponse().getContentAsString(), type);
        } catch (IOException e) {
            throw new RuntimeException("Error while converting from json to Java List", e);
        }
    }

    protected T fromJsonResult(MvcResult result) {
        try {
            return this.mapper.readValue(result.getResponse().getContentAsString(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error while converting from json to Java List", e);
        }
    }

    protected byte[] toJson(T object) throws Exception {
        return this.mapper.writeValueAsString(object).getBytes();
    }

}
