package com.mooreliu.model.product;

/**
 * Created by mooreliu on 2015/9/27.
 */
public class MerchandiseModel {
	private static final String TAG = "MerchandiseModel";
	private int merchandiseId;
	private String merchandiseName;
	private String merchandisePrice;
	private String merchandiseDescription;
	private String merchandiseImageUrl;

	public int getmerchandiseId() {
		return this.merchandiseId;
	}

	public void setmerchandiseId(int merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

	public String getmerchandiseName() {
		return this.merchandiseName;
	}

	public void setmerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public String getmerchandisePrice() {
		return this.merchandisePrice;
	}

	public void setmerchandisePrice(String merchandisePrice) {
		this.merchandisePrice = merchandisePrice;
	}

	public String getmerchandiseImageUrl() {
		return this.merchandiseImageUrl;
	}

	public void setmerchandiseImageUrl(String merchandiseImageUrl) {
		this.merchandiseImageUrl = merchandiseImageUrl;
	}

	public String getmerchandiseDescription() {
		return this.merchandiseDescription;
	}

	public void setmerchandiseDescription(String merchandiseDescription) {
		this.merchandiseDescription = merchandiseDescription;
	}
}
