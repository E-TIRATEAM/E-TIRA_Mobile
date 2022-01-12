package com.rememberdev.tirtaagung.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.activity.DetailPaketActivity;
import com.rememberdev.tirtaagung.activity.DetailPemesananTerkonfirmasiActivity;
import com.rememberdev.tirtaagung.activity.KonfirmasiPemesananActivity;
import com.rememberdev.tirtaagung.activity.ProfileActivity;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DaftarGambar;
import com.rememberdev.tirtaagung.model.Pemesanan;
import com.rememberdev.tirtaagung.model.PemesananUser;

import java.util.List;

public class AdapterPemesananUser extends RecyclerView.Adapter<AdapterPemesananUser.ViewHolder> {

    private Context ctx;
    private List<PemesananUser> listPemesanan;

    public AdapterPemesananUser(Context ctx, List<PemesananUser> listPemesanan) {
        this.ctx = ctx;
        this.listPemesanan = listPemesanan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pemesanan_user, parent, false);
        ViewHolder holder = new ViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PemesananUser daftarPemesanan = listPemesanan.get(position);
        Glide.with(holder.itemView.getContext())
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + daftarPemesanan.getGambar_satu())
                .centerCrop()
                .optionalCenterCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgPemesanan);
        holder.tvJudul.setText(daftarPemesanan.getJudul());
        holder.tvLamaKapasitas.setText(daftarPemesanan.getLama_sewa() + " | " + daftarPemesanan.getKapasitas());
        holder.tvTanggal.setText(daftarPemesanan.getTanggal_pemesanan());
        holder.tvHargaMetode.setText(daftarPemesanan.getHarga() + " | " + daftarPemesanan.getMetode_pembayaran());
        holder.tvStatus.setText(daftarPemesanan.getStatus_pemesanan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageStr =
                        "Konfirmasi pesanan atas nama :" + "\n" +
                        "*"+daftarPemesanan.getNama_lengkap()+"*" + "\n \n" +
                        "Detail pesanan :" + "\n" +
                        "~ "+daftarPemesanan.getNo_paket() + " " + daftarPemesanan.getJudul() + "\n" +
                        "~ "+daftarPemesanan.getLama_sewa() + " hari | " + daftarPemesanan.getKapasitas() + " orang \n" +
                        "~ "+daftarPemesanan.getFasilitas() + "\n" +
                        "~ "+daftarPemesanan.getTanggal_pemesanan() + "\n" +
                        "~ Rp."+daftarPemesanan.getHarga() + "\n \n" +
                        "Metode Pembayaran :" + "\n" +
                        "*"+daftarPemesanan.getMetode_pembayaran()+"*";
                String urlNew = "https://api.whatsapp.com/send?phone="+"+6282338906182"+"&text="+messageStr;
                if (daftarPemesanan.getStatus_pemesanan().equalsIgnoreCase("Belum Terkonfirmasi")){
                    new AlertDialog.Builder(ctx)
                            .setTitle("Pesan")
                            .setMessage("Pesanan " + daftarPemesanan.getStatus_pemesanan() + ", lanjut konfirmasi?")
                            .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(ctx, "konfirmasi", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(urlNew));
                                    ctx.startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(ctx)
                            .setTitle("Pesan")
                            .setMessage("Pesanan ini telah " + daftarPemesanan.getStatus_pemesanan())
                            .setPositiveButton("Lihat detail", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ctx, DetailPemesananTerkonfirmasiActivity.class);
                                    intent.putExtra("nama_lengkap", daftarPemesanan.getNama_lengkap());
                                    intent.putExtra("judul", daftarPemesanan.getJudul());
                                    intent.putExtra("harga", daftarPemesanan.getHarga());
                                    intent.putExtra("lama_sewa", daftarPemesanan.getLama_sewa());
                                    intent.putExtra("kapasitas", daftarPemesanan.getKapasitas());
                                    intent.putExtra("fasilitas", daftarPemesanan.getFasilitas());
                                    intent.putExtra("tanggal_pemesanan", daftarPemesanan.getTanggal_pemesanan());
                                    intent.putExtra("metode_pembayaran", daftarPemesanan.getMetode_pembayaran());
                                    intent.putExtra("status_pemesanan", daftarPemesanan.getStatus_pemesanan());
                                    ctx.startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
//        return listPemesanan.size();
        if (listPemesanan == null) {
            return 0;
        } else {
            return listPemesanan.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPemesanan;
        TextView tvJudul, tvLamaKapasitas, tvTanggal, tvHargaMetode, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPemesanan = itemView.findViewById(R.id.img_pemesanan);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvLamaKapasitas = itemView.findViewById(R.id.tv_lama_dan_kapasitas);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvHargaMetode = itemView.findViewById(R.id.tv_harga_dan_metode);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
