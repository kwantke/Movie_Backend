package kr.or.kkwk.common.exption;

public class DataIllegalArgumentException extends RuntimeException{

    public DataIllegalArgumentException(){

    }

    public DataIllegalArgumentException(String message){
        super(message);
    }

    public DataIllegalArgumentException(String message, Throwable cause){
        super(message, cause);
    }

    public DataIllegalArgumentException(Throwable cause){
        super(cause);
    }
}
