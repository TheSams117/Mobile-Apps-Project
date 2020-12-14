package com.example.entregaaplicacionesmoviles.model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;

public class StoreViewModel extends RecyclerView.ViewHolder {

    private TextView storeName,productOneName,productTwoName,productThreeName,productPriceOne, productPriceTwo,productPriceThree;
    private ImageView storeImage, productOneImage, productTwoImage,productThreeImage;
    private Button seeAll;

    public StoreViewModel(@NonNull View itemView) {
        super(itemView);
        storeImage = itemView.findViewById(R.id.profileImageSV);
        productOneImage = itemView.findViewById(R.id.productOneImageSV);
        productTwoImage = itemView.findViewById(R.id.productTwoImageSV);
        productThreeImage = itemView.findViewById(R.id.productThreeImageSV);
        seeAll = itemView.findViewById(R.id.seeAllSV);
        storeName = itemView.findViewById(R.id.storeNameSV);
        productOneName = itemView.findViewById(R.id.productOneNameSV);
        productTwoName = itemView.findViewById(R.id.productTwoNameSV);
        productThreeName = itemView.findViewById(R.id.productThreeNameSV);
        productPriceOne = itemView.findViewById(R.id.productPriceOneSV);
        productPriceTwo = itemView.findViewById(R.id.productPriceTwoSV);
        productPriceThree = itemView.findViewById(R.id.productPriceThreeSV);
    }

    public TextView getStoreName() {
        return storeName;
    }

    public void setStoreName(TextView storeName) {
        this.storeName = storeName;
    }

    public TextView getProductOneName() {
        return productOneName;
    }

    public void setProductOneName(TextView productOneName) {
        this.productOneName = productOneName;
    }

    public TextView getProductTwoName() {
        return productTwoName;
    }

    public void setProductTwoName(TextView productTwoName) {
        this.productTwoName = productTwoName;
    }

    public TextView getProductThreeName() {
        return productThreeName;
    }

    public void setProductThreeName(TextView productThreeName) {
        this.productThreeName = productThreeName;
    }

    public TextView getProductPriceOne() {
        return productPriceOne;
    }

    public void setProductPriceOne(TextView productPriceOne) {
        this.productPriceOne = productPriceOne;
    }

    public TextView getProductPriceTwo() {
        return productPriceTwo;
    }

    public void setProductPriceTwo(TextView productPriceTwo) {
        this.productPriceTwo = productPriceTwo;
    }

    public TextView getProductPriceThree() {
        return productPriceThree;
    }

    public void setProductPriceThree(TextView productPriceThree) {
        this.productPriceThree = productPriceThree;
    }

    public ImageView getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(ImageView storeImage) {
        this.storeImage = storeImage;
    }

    public ImageView getProductOneImage() {
        return productOneImage;
    }

    public void setProductOneImage(ImageView productOneImage) {
        this.productOneImage = productOneImage;
    }

    public ImageView getProductTwoImage() {
        return productTwoImage;
    }

    public void setProductTwoImage(ImageView productTwoImage) {
        this.productTwoImage = productTwoImage;
    }

    public ImageView getProductThreeImage() {
        return productThreeImage;
    }

    public void setProductThreeImage(ImageView productThreeImage) {
        this.productThreeImage = productThreeImage;
    }

    public Button getSeeAll() {
        return seeAll;
    }

    public void setSeeAll(Button seeAll) {
        this.seeAll = seeAll;
    }
}
