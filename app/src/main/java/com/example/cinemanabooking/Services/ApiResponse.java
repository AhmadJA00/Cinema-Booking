package com.example.cinemanabooking.Services;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {
    public  Integer HttpStateCode;
    public  Boolean IsSuccess= false;
    public ArrayList<String> ErrorMessage = new ArrayList<>();
    public Object Result;

}
