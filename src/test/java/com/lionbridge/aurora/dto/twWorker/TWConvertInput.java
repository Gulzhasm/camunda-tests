package com.lionbridge.aurora.dto.twWorker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TWConvertInput {
       public SourceFile sourceFile;
       public String sourceLanguage;
       public TWConfig twConfig;
       public String operation;
   }