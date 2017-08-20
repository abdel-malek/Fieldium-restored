package com.bluebrand.fieldium.core.model;

import android.content.Context;

import com.bluebrand.fieldium.R;

/**
 * Created by Farah Etmeh on 5/9/16.
 */
public enum BookingStatus {
    Pending(1), Approved(2), Declined(3), Canceled(4);
    private int id;

    BookingStatus(int id) {
        this.setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(Context context) {
        if (this == Approved)
            return context.getResources().getString(R.string.received);
        else if (this == Declined)
            return context.getResources().getString(R.string.rejected);
        else if (this == Canceled)
            return context.getResources().getString(R.string.canceled);
        else return context.getResources().getString(R.string.pending);
    }

    public static BookingStatus getById(int id) {
        switch (id) {

            case (1):
                return Pending;

            case (2):
                return Approved;

            case (3):
                return Declined;
            case (4):
                return Canceled;

            default:
                return Pending;

        }
    }
}
