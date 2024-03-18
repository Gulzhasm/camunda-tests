package com.lionbridge.aurora.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusCollection {
    public String sourceLanguage;
    public String targetLanguage;
    public OptimusConvertedFile[] files;
}
