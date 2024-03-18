package com.lionbridge.aurora.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusConvertedFile {
    public String sourceLanguage;
    public FilterType filterType;
    public String targetLanguage;
    public String original;
    public String converted;
}
