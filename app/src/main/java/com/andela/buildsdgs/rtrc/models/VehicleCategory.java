package com.andela.buildsdgs.rtrc.models;

public class VehicleCategory {
    private String id;
    private String name;
    private String toll_fee;
    private boolean active;
    private String created_at;
    private String updated_at;
    private String image_url;

    public VehicleCategory(String name, String toll_fee, String image_url) {
        this.name = name;
        this.toll_fee = toll_fee;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToll_fee() {
        return toll_fee;
    }

    public void setToll_fee(String toll_fee) {
        this.toll_fee = toll_fee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    //     "id": "6d41eddd-8f44-485e-9fc0-c1a7b5a665aa",
//             "name": "PICK-UP/VANS",
//             "toll_fee": "1.00",
//             "active": true,
//             "created_at": "2020-05-28T23:31:02.606315-05:00",
//             "updated_at": "2020-05-28T23:31:02.606343-05:00"
}
