package com.casa.aems.identity.exception;

public class ErrorCodes {

    public static final int AUTHENTICATION_ERROR = 1;
    
    public static final int GENERAL_ERROR = 100;
    
    public static final int VALIDATION_ERROR = 200;
    
    public static final int ENTITY_NOT_FOUND = 404;
    public static final int DUPLICATE_ENTITY_ERROR = 405;
    
    public static final int DBUPDATE_ERROR = 500;
    public static final int DBINSERT_ERROR = 501;
    public static final int DBRETRIEVE_ERROR = 502;
    
    public static final int RESOURCE_NOT_EMPTY_ERROR = 601;
    public static final int RESOURCE_BUSY_ERROR = 602;
    public static final int RESOURCE_LOCKED_ERROR = 603;
    public static final int RESOURCE_DELETE_ERROR = 604;
    
    public static final int CLI_EXECUTION_ERROR = 701;
    public static final int CLI_WF_DEFINITON_ERROR = 702;
    public static final int CLI_CONNECT_ERROR = 703;
    public static final int CLI_ERROR = 704;
    public static final int CLI_PARSE_ERROR = 705;
 
    public static final int VNF_SCALE_VALIDATION_ERROR = 801;
    public static final int VNF_SCALEUP_ERROR = 802;
    public static final int VNF_SCALEDOWN_ERROR = 803;
    public static final int VNF_SCALEOUT_ERROR = 804;
    public static final int VNF_SCALEIN_ERROR = 805;
}
