package com.camunda.tests.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPreparation {
    public String dtp_policy = "FILE_TYPE_DRIVEN";
    public String eng_policy  ="FILE_TYPE_DRIVEN";
    public String modify_source_script;
    public String xliff_grouping_policy = "TARGET_LANG";
    public String check_xliff_script;
    public String post_xliff_script;
}
