package in.nic.assam.udyogmitra.model;

import androidx.annotation.NonNull;

public class District {

    String districtName,districtCode;


    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @NonNull
    @Override
    public String toString() {
        return districtName;
    }

}
