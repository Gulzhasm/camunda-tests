package com.camunda.tests.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geofluent {
    public String account_key;
    public String account_secret;
    public boolean is_llm_post_editing_requested = false;
}
