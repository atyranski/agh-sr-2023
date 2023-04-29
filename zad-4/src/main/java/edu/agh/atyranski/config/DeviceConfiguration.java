package edu.agh.atyranski.config;

import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectPrx;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfiguration {

    private String id;
    private String name;
    private Class<? extends ObjectPrx> proxyType;
    private Class<? extends Object> servantType;
}
