package com.enigmacamp.springbootdatapenduduk.config;

public class AppRoutes {
    public static final String API_VERSION = "/api/v1";
    public static final String LOGIN = "/auth/login";
    public static final String REGISTER = "/auth/register";

    public static final String POST_PROVINCE = "provinces";
    public static final String GET_PROVINCE_LIST = "provinces";
    public static final String GET_PROVINCE = "provinces/{id}";
    public static final String PUT_PROVINCE = "provinces";
    public static final String DELETE_PROVINCE = "provinces/{id}";

    public static final String POST_REGENCY = "regencies";
    public static final String GET_REGENCY_LIST = "regencies";
    public static final String GET_REGENCY_BY_PROVINCE = "regencies/province/{id}";
    public static final String GET_REGENCY = "regencies/{id}";
    public static final String PUT_REGENCY = "regencies";
    public static final String DELETE_REGENCY = "regencies/{id}";

    public static final String POST_DISTRICT = "districts";
    public static final String GET_DISTRICT_LIST = "districts";
    public static final String GET_DISTRICT_BY_REGENCY = "districts/regency/{id}";
    public static final String GET_DISTRICT = "districts/{id}";
    public static final String PUT_DISTRICT = "districts";
    public static final String DELETE_DISTRICT = "districts/{id}";

    public static final String POST_PEOPLE = "people";
    public static final String GET_PEOPLE_LIST = "people";
    public static final String GET_PEOPLE_BY_PROVINCE = "people/province/{id}";
    public static final String GET_PEOPLE_BY_REGENCY = "people/regency/{id}";
    public static final String GET_PEOPLE_BY_DISTRICT = "people/district/{id}";
    public static final String GET_PEOPLE = "people/{id}";
    public static final String PUT_PEOPLE = "people";
    public static final String DELETE_PEOPLE = "people/{id}";
}
