package ru.valera.bot.model.crypta;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
public class MinerstateGPU extends AbstractGPUClass {
    private String id;
    private String name;
    //    private String url;
    private String type;
    private String brand;
    private Map<String, Algoritm> algorithms;
//    private Specs specs;

    @Override
    String getNameDevices(String name) {
        return name;
    }

    @Override
    double getHashrateForAlgo(String algo) {
        return algorithms.get(algo).speed;
    }

    @Override
    double getPower(String algo) {
        return algorithms.get(algo).power;
    }

    @Getter
    @ToString
    public static class Algoritm {
        private double speed;
        private int power;
    }

//    @Getter
//    public static class Specs {
//
//        private String release;
//        private String ratedPower;
//        private String powerConnectors;
//        private String memorySize;
//        private String baseClock;
//        private String boostClock;
//        private String GPUPower;
//        private String maxMemorySize;
//        private String maxMemoryBandwidth;
//        private String memoryType;
//        private String maxTemp;
//        private String CUDACores;
//        private String memoryInterface;
//        private double openGL;
//    }
}
