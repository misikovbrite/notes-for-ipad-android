package com.britetodo.notesforandroid.ui.screens.paywall

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.britetodo.notesforandroid.billing.BillingManager
import com.britetodo.notesforandroid.billing.ProductIds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val billingManager: BillingManager,
) : ViewModel() {

    val products: StateFlow<List<ProductDetails>> = billingManager.products

    fun purchase(planIndex: Int, activity: Activity? = null) {
        val productId = when (planIndex) {
            0 -> ProductIds.YEARLY_TRIAL
            1 -> ProductIds.MONTHLY
            2 -> ProductIds.YEARLY
            else -> ProductIds.YEARLY_TRIAL
        }
        val product = products.value.firstOrNull { it.productId == productId } ?: return
        val offerToken = product.subscriptionOfferDetails?.firstOrNull()?.offerToken
        activity?.let { billingManager.launchBillingFlow(it, product, offerToken) }
    }

    fun restorePurchases() = viewModelScope.launch {
        billingManager.queryPurchases()
    }
}
