package com.andrea.belotti.brorkout.model.nodes;

import com.andrea.belotti.brorkout.model.Scheda;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlanCompletedNode implements Serializable, BaseNode {

    private Long id;
    private Long parentId;
    private String name;
    private Scheda plan;

    public PlanCompletedNode(Long id, Long parentId, String name, Scheda plan) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.plan = plan;
    }

    public PlanCompletedNode() {}

    public boolean isEmpty() {
        return plan == null;
    }

    // ============================================================================================
    // GETTER AND SETTER
    // ============================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scheda getPlan() {
        return plan;
    }

    public void setPlan(Scheda plan) {
        this.plan = plan;
    }
}
