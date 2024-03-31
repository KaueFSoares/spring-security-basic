package br.com.kauesoares.simplespringsecurityproject.project.base;

import br.com.kauesoares.simplespringsecurityproject.project.util.AuthUtil;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    protected Boolean deleted;
    protected String createdBy;
    protected String updatedBy;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @PrePersist
    private void beforeInsert() {
        this.deleted = false;
        this.createdAt = LocalDateTime.now();
        this.createdBy = AuthUtil.getUserName();
        this.updatedAt = this.createdAt;
        this.updatedBy = this.createdBy;
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = AuthUtil.getUserName();
    }
}