package com.lionbridge.aurora.dto.e2e;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessConfig {
      public OrderAllocation order_allocation;
      public FileDelivery file_delivery;
      public FileTypeDetection file_type_detection;
      public TWFileConversion tw_file_conversion;
      public OrderPreparation order_preparation;
      public Geofluent geofluent;
  }
