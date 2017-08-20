package fr.company.agterra.dofusmanager.Objects;

import fr.company.agterra.dofusmanager.EffectType;

/**
 * Created by Agterra on 20/08/2017.
 */

public class Effect {

    private String minValue;

    private String maxValue;

    private EffectType type;

    public Effect() {

        this.minValue = String.valueOf(0);

        this.maxValue = String.valueOf(0);

        this.type = EffectType.UNKNOWN_EFFECT_TYPE;

    }

    public Effect(String minValue, String maxValue, EffectType type) {

        this.minValue = minValue;

        this.maxValue = maxValue;

        this.type = type;

    }

    public String getMinValue() {

        return minValue;

    }

    public void setMinValue(String minValue) {

        this.minValue = minValue;

    }

    public String getMaxValue() {

        return maxValue;

    }

    public void setMaxValue(String maxValue) {

        this.maxValue = maxValue;

    }

    public EffectType getType() {

        return type;

    }

    public void setType(EffectType type) {

        this.type = type;

    }

    @Override
    public String toString() {

        return "Effect{" +
                "minValue='" + minValue + '\'' +
                ", maxValue='" + maxValue + '\'' +
                ", type=" + type +
                '}';

    }

}
