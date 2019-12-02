package com.chins.blog.backend.commons.base;

import java.util.Map;
import lombok.Data;

@Data
public class RequestBase {

  private Map<String, Object> data;

}
