package com.andrea.belotti.brorkout.model.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Node implements Serializable, BaseNode {

    private Long id;
    private String name;
    private Long parentId;
    private List<Node> children = new ArrayList<>();
    private List<PlanCompletedNode> data;

    // ============================================================================================
    // PUBLIC METHOD
    // ============================================================================================

    public Node(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Node() {
    }

    public boolean isEmpty() {
        return (children == null || children.isEmpty()) && data == null;
    }

    public boolean hasChild() {
        return children != null && !children.isEmpty();
    }

    public boolean hasData() {
        return data != null && data.isEmpty();
    }





    public BaseNode findChildById(Long id, List<Node> children) {

        for (Node node : children) {
            if (node.getId().equals(id)) {
                return node;
            } else {
                return findChildById(id, node.getChildren());
            }
        }

        return null;
    }

    public BaseNode findDataById(Long id) {

        for (PlanCompletedNode node : this.data) {
            if (node.getId().equals(id)) {
                return node;
            }
        }

        return null;
    }


    // ============================================================================================
    // GETTER AND SETTER
    // ============================================================================================

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlanCompletedNode> getData() {
        return data;
    }

    public void setData(List<PlanCompletedNode> data) {
        this.data = data;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
