package org.sang.bean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: tanlin
 * @Date: 2020/11/02/16:38
 * @Description:
 */
public class CeshiDate {
    private String name;
    private String strValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public String toString() {
        return "CeshiDate{" +
                "name='" + name + '\'' +
                ", strValue='" + strValue + '\'' +
                '}';
    }
}
