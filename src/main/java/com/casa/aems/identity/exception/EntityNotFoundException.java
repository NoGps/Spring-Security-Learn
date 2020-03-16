package com.casa.aems.identity.exception;

public class EntityNotFoundException extends AppException {
    private static final long serialVersionUID = 1L;

    protected String entityType;

    protected String entityId;

    public EntityNotFoundException(String entityType, String entityId,
            String principal, String message, Throwable ex) {
        super(ErrorCodes.ENTITY_NOT_FOUND,message, ex);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public EntityNotFoundException(String entityType, String entityId,
			String principal, String message) {
        super(ErrorCodes.ENTITY_NOT_FOUND,message);
        this.entityType = entityType;
        this.entityId = entityId;
	}

	public EntityNotFoundException(String entityType, String entityId, String message) {
        super(ErrorCodes.ENTITY_NOT_FOUND,message);
        this.entityType = entityType;
        this.entityId = entityId;
	}

	public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
