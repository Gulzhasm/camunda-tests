package com.camunda.tests.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusDeliveryFile {
    public String sourceLanguage;
    public FilterType filterType;
    public String deliverable;
    public String translated;
    public String targetLanguage;
    public String original;
    public String converted;
}
