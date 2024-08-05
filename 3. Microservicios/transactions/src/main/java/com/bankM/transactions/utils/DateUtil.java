package com.bankM.transactions.utils;

import com.bankM.transactions.exceptions.TransactionException;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static void validateDates(Date startDate, Date endDate) throws TransactionException {
        if(startDate.after(endDate)){
            throw new TransactionException(HttpStatus.BAD_REQUEST,"Se debe ingresar primero la fecha inicial y luego la final");
        }
    }

    public static Date dateToEndOfDay(Date endDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String changeDateToLocalDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato deseado
        return dateFormat.format(date);
    }
}
