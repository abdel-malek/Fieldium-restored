package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.fieldium.view.Booking.MyVouchersActivity;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class MyVouchersAdapter extends ArrayAdapter<Voucher> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //    private ArrayList<Voucher> vouchersList = new ArrayList<>();
    FoldingCell mCell;
    int voucherPos;
    boolean unfold = true;
    private View.OnClickListener defaultRequestBtnClickListener;

    public MyVouchersAdapter(Context context, int resource, List<Voucher> objects, int voucherPosition) {
        super(context, resource, objects);
        this.voucherPos = voucherPosition;
//        this.vouchersList.addAll(objects);
    }

    @Override
    public int getViewTypeCount() {
        int n = getCount();
        if (n == 0)
            return 1;
        else
            return n;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        FoldingCell cell = (FoldingCell) convertView;
        if (cell == null) {
            cell = (FoldingCell) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.voucher_item, null);
            holder = new ViewHolder();

            holder.textView_voucher_code = (TextView) cell.findViewById(R.id.voucher_code);
            holder.textView_days_left = (TextView) cell.findViewById(R.id.voucher_days_left);
            holder.textView_voucher_type = (TextView) cell.findViewById(R.id.voucher_type);
            holder.imageView_game_content = (ImageView) cell.findViewById(R.id.imageView_game_content);
            holder.imageView_game = (ImageView) cell.findViewById(R.id.imageView_game);
            holder.voucher_code_content = (TextView) cell.findViewById(R.id.voucher_code_content);
            holder.voucher_days_left_content = (TextView) cell.findViewById(R.id.voucher_days_left_content);
            holder.voucher_value_content = (TextView) cell.findViewById(R.id.voucher_value_content);
            holder.voucher_timeFrom_content = (TextView) cell.findViewById(R.id.voucher_timeFrom_content);
            holder.voucher_timeTo_content = (TextView) cell.findViewById(R.id.voucher_timeTo_content);
            holder.voucher_startDate_content = (TextView) cell.findViewById(R.id.voucher_startDate_content);
            holder.voucher_expiryDate_content = (TextView) cell.findViewById(R.id.voucher_expiryDate_content);
            holder.voucher_desc_content = (TextView) cell.findViewById(R.id.voucher_desc_content);

            holder.companiesLayout = (LinearLayout) cell.findViewById(R.id.voucher_companies);

            cell.setTag(holder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            holder = (ViewHolder) cell.getTag();
        }

        final Voucher voucher = (Voucher) getItem(position);

        holder.textView_voucher_code.setText(getContext().getResources().getString(R.string.code) + voucher.getCode());
        holder.voucher_code_content.setText(getContext().getResources().getString(R.string.code) + voucher.getCode());
//        String daysLeft = String.valueOf(daysLeft(voucher.getExpiry_date()));
        String daysLeftString = ((MyVouchersActivity)getContext()).daysLeft(voucher.getExpiry_date()) + " " + getContext().getResources().getString(R.string.days_left);
    /*    Spannable spannable = new SpannableString(daysLeftString);
        spannable.setSpan(new ForegroundColorSpan(*//*getContext().getResources().getColor(R.color.colorAccent)*//*Color.BLUE),
                daysLeftString.length(), daysLeft .length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textView_days_left.setText(spannable, TextView.BufferType.SPANNABLE);*/
        holder.textView_days_left.setText(daysLeftString);
        holder.voucher_days_left_content.setText(daysLeftString);
        if (voucher.getType() == 2) {
            String hDuration = (voucher.getValue() / 60) < 10 ? "0" + voucher.getValue() / 60 : voucher.getValue() / 60 + "";
            String mDuration = (voucher.getValue() % 60) < 10 ? "0" + voucher.getValue() % 60 : voucher.getValue() % 60 + "";
            holder.textView_voucher_type.setText(hDuration + ":" + mDuration + "\n" + getContext().getResources().getString(R.string.free));
//            holder.voucher_type_content.setText(hDuration + ":" + mDuration + "\n" + getContext().getResources().getString(R.string.free));
            holder.voucher_value_content.setText(hDuration + ":" + mDuration + " " + getContext().getResources().getString(R.string.free));
        } else {
            holder.textView_voucher_type.setText(voucher.getValue() + "%");
//            holder.voucher_type_content.setText(voucher.getValue() + "%");
            holder.voucher_value_content.setText(voucher.getValue() + "%");
        }
        if (!voucher.getFrom_hour().equals("null") && !voucher.getTo_hour().equals("null")) {
            cell.findViewById(R.id.fromTimeRow).setVisibility(View.VISIBLE);
            cell.findViewById(R.id.toTimeRow).setVisibility(View.VISIBLE);
            holder.voucher_timeFrom_content.setText(voucher.getFrom_hour());
            holder.voucher_timeTo_content.setText(voucher.getTo_hour());
        }
        holder.voucher_startDate_content.setText(voucher.getStart_date());
        holder.voucher_expiryDate_content.setText(voucher.getExpiry_date());
        holder.voucher_desc_content.setText(voucher.getDescription());

        if (voucher.getGames().size() == 1) {
            try {
                Picasso.with(getContext()).load(voucher.getGames().get(0).getImageUrl())
                        .error(R.mipmap.logo)
//                        .fitCenter()
                        .placeholder(R.mipmap.logo)
                        .into(holder.imageView_game_content);
            } catch (Exception e) {
                holder.imageView_game_content.setImageResource(R.mipmap.logo);
            }
            try {
                Picasso.with(getContext()).load(voucher.getGames().get(0).getImageUrl())
                        .error(R.mipmap.logo)
//                        .fitCenter()
                        .placeholder(R.mipmap.logo)
                        .into(holder.imageView_game);
            } catch (Exception e) {
                holder.imageView_game.setImageResource(R.mipmap.logo);
            }
        }
        if (voucher.getCompanies().size() != 0) {
            cell.findViewById(R.id.companies_row).setVisibility(View.VISIBLE);
            holder.companiesLayout.removeAllViews();
            for (int i = 0; i < voucher.getCompanies().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                TextView textView_companyName = new TextView(getContext());
                TextView textView_companyCity = new TextView(getContext());

                //image
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(3, 3, 3, 3);
                imageView.setLayoutParams(layoutParams);
                try {
                    Picasso.with(getContext()).load(voucher.getCompanies().get(i).getLogoURL())
                            .error(R.drawable.default_image)
//                        .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imageView);
                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.default_image);
                }
                //co. name
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.CENTER;
                layoutParams1.setMargins(3, 3, 3, 3);
                textView_companyName.setTextColor(getContext().getResources().getColor(R.color.white_text));
                textView_companyName.setLayoutParams(layoutParams1);
                textView_companyName.setText(voucher.getCompanies().get(i).getName());

                //co. city
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.gravity = Gravity.CENTER;
                layoutParams2.setMargins(3, 3, 3, 3);
                textView_companyCity.setTextColor(getContext().getResources().getColor(R.color.white_text));
                textView_companyCity.setLayoutParams(layoutParams2);

                LinearLayout linearLayout = new LinearLayout(getContext());

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams3.setMargins(5, 5, 5, 5);
                linearLayout.setLayoutParams(layoutParams3);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);

                linearLayout.addView(imageView);
                linearLayout.addView(textView_companyName);
                linearLayout.addView(textView_companyCity);
                final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Toast.makeText(getContext(), "" + voucher.getCompanies().get(finalI).getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), FieldsListActivity.class);
                        intent.putExtra("company_id", voucher.getCompanies().get(finalI).getId());
                        intent.putExtra("voucher_id", voucher.getId());
                        if (voucher.getGames().size() == 1) {
                            ((FieldiumApplication) ((MyVouchersActivity) getContext()).getApplication()).setGame_id(/*voucher.getGames().get(0).getId()*/0);
//                            ((FieldiumApplication) ((MyVouchersActivity) getContext()).getApplication()).getBooking().setGame(voucher.getGames().get(0));
                        } else
                            ((FieldiumApplication) ((MyVouchersActivity) getContext()).getApplication()).setGame_id(0);

                        ((MyVouchersActivity) getContext()).startActivity(intent);
                    }
                });
                holder.companiesLayout.addView(linearLayout, i);
            }
        }

        final FoldingCell finalCell = cell;
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle clicked cell state
                finalCell.toggle(false);
                // register in adapter that state for selected cell is toggled
                registerToggle(position);
            }
        });
        cell.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (position == voucherPos && unfold) {
                    unfold = false;
                    voucherPos = -1;
                    finalCell.toggle(false);
                    // register in adapter that state for selected cell is toggled
                    registerToggle(position);
                }
            }
        });
        /*if (position == voucherPos && voucherPos != -1) {

            cell.toggle(false);
            // register in adapter that state for selected cell is toggled
            registerToggle(position);
        }*/
        /*if (voucher.getRequestBtnClickListener() != null) {
            holder.textView_name.setOnClickListener(voucher.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            holder.textView_name.setOnClickListener(defaultRequestBtnClickListener);
        }*/

        return cell;
    }

//    public FoldingCell getCell() {
//    return mCell;
//    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

   /* public int daysLeft(String expiryDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = null;
        try {
            d = formatter.parse(expiryDate);//catch exception


            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(d);

            Calendar today = Calendar.getInstance();

            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            return (int) days < 0 ? -1 * (int) days : (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }*/

    static class ViewHolder {
        TextView textView_voucher_code;
        TextView textView_days_left;
        TextView textView_voucher_type;
        TextView voucher_days_left_content;
        TextView voucher_code_content;
        TextView voucher_value_content;
        TextView voucher_timeFrom_content;
        TextView voucher_timeTo_content;
        TextView voucher_startDate_content;
        TextView voucher_expiryDate_content;
        TextView voucher_desc_content;
        ImageView imageView_game_content;
        ImageView imageView_game;
        LinearLayout companiesLayout;
    }
}
