package copying;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeepClone {
   public static <T> T deepCloneJackson(T objectToClone) throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(objectToClone);
      return objectMapper.readValue(json, (Class<T>) objectToClone.getClass());
   }
}
