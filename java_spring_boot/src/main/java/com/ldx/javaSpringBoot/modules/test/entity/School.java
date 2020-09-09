package com.ldx.javaSpringBoot.modules.test.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "h_school")//建表
public class School {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schoolId;//列名
    private int schoolName;
    @Transient
    private String region;

    /**
     * OneToMany：多方使用 joinClumn，创建外键，一方使用 mappedBy 属性
     * cascade：联级操作
     * fetch：加载数据策略
     */
    //school(mappedBy one——many  JoinColumn)clazz 多方使用JoinColumn，创立外键，一方使用mappedBy
    @OneToMany(mappedBy = "school", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(int schoolName) {
        this.schoolName = schoolName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }
}
