package ru.bot.valera.bot.model.crypta;

public abstract class AbstractGPUClass {

    abstract String getNameDevices(String name);

    abstract double getHashrateForAlgo(String algo);

    abstract double getPower(String algo);
}
