package com.lionbridge.aurora.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusConvertOutput {
    public int jobId;
    public ProcessConfig config;
    public OptimusDeliveryFile[][] allFiles;
    public String workType;
    public OptimusCollection[] collections;
    public OptimusSourceFile[] sourceFiles;
    public OptimusConvertedFile[] convertedFiles;
    public String sourceLanguage;
    public String[] targetLanguages;
}