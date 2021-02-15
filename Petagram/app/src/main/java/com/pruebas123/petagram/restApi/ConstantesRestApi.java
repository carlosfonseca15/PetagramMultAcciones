package com.pruebas123.petagram.restApi;

public final class ConstantesRestApi {

    public final static String VERSION = "/v9.0/";
    public final static String ROOT_URL = "https://graph.facebook.com" + VERSION;
    //public final static String ID = "17841444761449945";
    public final static String ID = "17841402381545326";
    public final static String FIELDS = "?fields=username%2Cmedia_count%2Cmedia";
    public final static String MEDIA_FIELDS = "?fields=media_url,like_count";
    //public final static String ACCESS_TOKEN = "EAAF94fo2sjABAJlAuVe3S8qzKnpp5ZCFfeFAxYG6qQ9xgqwekE3nijTRQmJRSVGoy81lqa0PysZBSqviFdyq9TOUg0YQgXJqVOnNGmOzZAz1aQuISy0A6So3TEOb90TeDXvxQZCCxuE6oNCg7QtA8aPG0r5EDmwmLGIlf4OORQZDZD";
    public final static String ACCESS_TOKEN = "EAAafC3tiYRUBAHz8wvd2irT9Y3pKRU7tFM5fiPcFZALH1mwGWTqgiEItKdyHZAduh3ccbyIcAYkz7k7QpTObZBZBhtZCJr7taJMeouH4GLVWj0mAU6OAsyVzVwZCEnzCddjcWnr24MQMV817qbmzDHvZBs9IUeNHK8ZD";
    public final static String KEY_ACCESS_TOKEN =  "&access_token=";
    public final static String KEY_GET_USER_INFO =  "users/self/media/recent/";
    public final static String GET_USR_INFO = ID + FIELDS + KEY_ACCESS_TOKEN + ACCESS_TOKEN;
    public final static String GET_MEDIA_INFO = MEDIA_FIELDS + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

}
