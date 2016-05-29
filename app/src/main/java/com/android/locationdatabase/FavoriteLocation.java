package com.android.locationdatabase;

/**
 * Created by JinghongXu on 5/28/16.
 */
public class FavoriteLocation{


    public String name;
    public double latitude;
    public double longitude;
    public boolean visiting;
    public String RingTone;


    public FavoriteLocation(String str, double lat, double log)
    {

        this.name = str;
        this.latitude = lat;
        this.longitude = log;
        this.visiting = false;
        this.RingTone = "default";

    }

    public FavoriteLocation(String str1, double lat, double log, String str2)
    {

        this.name = str1;
        this.latitude = lat;
        this.longitude = log;
        this.visiting = false;
        this.RingTone = str2;

    }

    public String getName()
    {

        return this.name;

    }

    public double getLatitude()
    {

        return this.latitude;

    }

    public double getLongitude()
    {

        return this.longitude;

    }

    public String getRingTone()
    {

        return this.RingTone;

    }

    public void setName(String str)
    {

        this.name = str;

    }

    public void setLatitude(double lat)
    {

        this.latitude = lat;

    }

    public void setLongitude(double log)
    {

        this.longitude = log;

    }

    public void setRingTone(String str)
    {

        this.RingTone = str;

    }


    public boolean isVisiting()
    {

        return this.visiting;

    }

    public void setVisit(boolean visiting)
    {

        this.visiting = visiting;

    }


    public boolean equals(FavoriteLocation object)
    {
        if (object != null)
        {
            if(this.name.equals(object.name)
                    && this.latitude == object.latitude
                    && this.longitude == object.longitude)
            {
                return true;
            }

        }

        return false;
    }


}
