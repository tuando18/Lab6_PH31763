package com.dovantuan.lab6_ph31763;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Activity_DSSV extends AppCompatActivity {

    AdapterDSSV adapterSv;
    ListView lvdssv;
    Context context=this;
    ArrayList<ListDssv> listSv = new ArrayList<>();
//    AdapterDSSV adapterSv;

    ActivityResultLauncher<Intent> getData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        String cs = b.getString(Activity_AddStudent.KEY_COSO);
                        String ten = b.getString(Activity_AddStudent.KEY_TEN_SV);
                        String dc = b.getString(Activity_AddStudent.KEY_DIA_CHI);
                        listSv.add(new ListDssv(cs, ten, dc));
                        fill();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lvdssv = findViewById(R.id.lv_sv);

        readWriteStudent readWriteStudent = new readWriteStudent(context);
        listSv=readWriteStudent.getDataOld(context, "student.txt");


//        // Khởi tạo dữ liệu danh sách
//        listSv.add(new ListDssv("FPoly Hà Nội", "Nguyễn Văn Tuấn", "Bắc Ninh"));
//        listSv.add(new ListDssv("FPoly Hồ Chí Minh", "Đỗ Văn Tuấn", "Tây Ninh"));
//        listSv.add(new ListDssv("FPoly Đà Nẵng", "Nguyễn Công Thưởng", "Nha Trang"));
//        listSv.add(new ListDssv("FPoly Tây Nguyên", "Nguyễn Vinh Tài", "Đắk Lắk"));
//        listSv.add(new ListDssv("FPoly Cần Thơ", "Cấn Gia Khiêm", "Kiên Giang"));
        fill();

    }
    public void fill() {
        adapterSv = new AdapterDSSV(Activity_DSSV.this, listSv, new DeleteItem() {
            @Override
            public void onClickForDelete(int pos) {
                readWriteStudent readWriteStudent2 =new readWriteStudent(context);
                readWriteStudent2.deleteOneStudent(context,listSv,pos,"student.txt");
            }
        }, new UpdateItem() {
            @Override
            public void onClickForUpdate(int pos, String branch,String name, String address) {
                readWriteStudent readWriteStudent1 =new readWriteStudent(context);
                readWriteStudent1.updateStudent(context,listSv,pos,new ListDssv(branch,name,address),"student.txt");

            }
        });
        lvdssv.setAdapter(adapterSv);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterSv.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterSv.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(Activity_DSSV.this, Activity_AddStudent.class);
            getData.launch(intent);
        } else if (item.getItemId() == R.id.menu_dangXuat) {
            Intent intent = new Intent(this, Activity_Login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static final String KEY_SV_MODEL = "sv_model";

    ActivityResultLauncher<Intent> goToEditSV = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        String cs = b.getString(Activity_AddStudent.KEY_COSO);
                        //Log.d("coso", "nhan " + cs);
                        String ten = b.getString(Activity_AddStudent.KEY_TEN_SV);
                        String dc = b.getString(Activity_AddStudent.KEY_DIA_CHI);

                        svModel.name = ten;
                        svModel.address = dc;
                        svModel.branch = cs;

                        fill();
                    }
                }
            }
    );

    private ListDssv svModel;

    public void updateSV(int position) {

        Intent intent = new Intent(Activity_DSSV.this, Activity_AddStudent.class);

        svModel = listSv.get(position);
        intent.putExtra(KEY_SV_MODEL, svModel);

        goToEditSV.launch(intent);
    }

    private class AdapterDSSV extends BaseAdapter implements Filterable {

        Activity activity;
        ArrayList<ListDssv> list, listOld;
        DeleteItem deleteItem;
        UpdateItem updateItem;

        public AdapterDSSV(Activity activity, ArrayList<ListDssv> list,DeleteItem deleteItem, UpdateItem updateItem) {
            this.activity = activity;
            this.list = list;
            this.listOld = list;
            this.updateItem= updateItem;
            this.deleteItem = deleteItem;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_listview, viewGroup, false);

            ListDssv listsv = list.get(i);

            TextView tvBranch = view.findViewById(R.id.tvBranch);
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvAddress = view.findViewById(R.id.tvAddress);

            tvBranch.setText(listsv.getBranch());
            tvName.setText(listsv.getName());
            tvAddress.setText(listsv.getAddress());

            Button btnRemove = view.findViewById(R.id.btn_remove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Xóa sinh viên");
                    builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItem.onClickForDelete(i);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            Button btnUpdate = view.findViewById(R.id.btn_Update);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    LayoutInflater inflater1 = ((Activity) activity).getLayoutInflater();
                    View view1 = inflater1.inflate(R.layout.activity_addstudent, null);
                    builder.setView(view1);

                    final EditText txtTen = view1.findViewById(R.id.edt_username);
                    final EditText txtDiaChi = view1.findViewById(R.id.edt_address);
                    final Spinner cs = view1.findViewById(R.id.sp_ngonngu);
                    builder.setTitle("Update Form");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ten = txtTen.getText().toString();
                            String diaChi = txtDiaChi.getText().toString();
                            updateItem.onClickForUpdate(i,"Hà Nội", ten, diaChi);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    notifyDataSetChanged();
//                    ((Activity_DSSV) activity).updateSV(i);
//                    Toast.makeText(activity, "Update thành công!", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String s = constraint.toString();
                    if (s.isEmpty()) {
                        list = listOld;
                    } else {
                        ArrayList<ListDssv> listS = new ArrayList<>();
                        for (ListDssv st : listOld) {
                            if (st.getName().toLowerCase().contains(s.toLowerCase())) {
                                listS.add(st);
                            }
                        }
                        list = listS;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = list;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    list = (ArrayList<ListDssv>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

}