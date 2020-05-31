package transport4future.TokenManagement.model;

/**
 * The enum Token operation type.
 */
public enum TokenOperationType {
    /**
     * Send information from sensor token operation type.
     */
    SendInformationFromSensor,
    /**
     * Send request to actuator token operation type.
     */
    SendRequestToActuator,
    /**
     * Check state token operation type.
     */
    CheckState
}
