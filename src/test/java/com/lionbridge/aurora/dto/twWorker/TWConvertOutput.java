package com.lionbridge.aurora.dto.twWorker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TWConvertOutput {
        public TWConfig twConfig;
        public String operation;
        public SourceFile sourceFile;
        public String convertedFile;
        public String sourceLanguage;
    }
