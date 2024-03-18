package com.lionbridge.aurora.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusConvertInput {
    public int jobId;
    public String workType;
    public String sourceLanguage;
    public String[] targetLanguages;
    public OptimusSourceFile[] sourceFiles;
    public ProcessConfig config;

}