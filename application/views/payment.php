<button id="paypal-button">Pay</button>
<script src="https://js.braintreegateway.com/web/3.22.0/js/client.min.js"></script>
<script src="https://js.braintreegateway.com/web/3.22.0/js/paypal-checkout.js"></script>

<!-- Use the components. We'll see usage instructions next. -->
<script>
    var token = "<?php echo $token ?>";
//    var client = require('braintree-web/client');
//    var paypalCheckout = require('braintree-web/paypal-checkout');

// Create a client.
    braintree.client.create({
        authorization: token
    }, function (clientErr, clientInstance) {

        // Stop if there was a problem creating the client.
        // This could happen if there is a network error or if the authorization
        // is invalid.
        if (clientErr) {
            console.error('Error creating client:', clientErr);
            return;
        }

        // Create a PayPal Checkout component.
        braintree.paypalCheckout.create({
            client: clientInstance
        }, function (paypalCheckoutErr, paypalCheckoutInstance) {

            // Stop if there was a problem creating PayPal Checkout.
            // This could happen if there was a network error or if it's incorrectly
            // configured.
            if (paypalCheckoutErr) {
                console.error('Error creating PayPal Checkout:', paypalCheckoutErr);
                return;
            }

            // Set up PayPal with the checkout.js library
            paypal.Button.render({
                env: 'sandbox', // or 'sandbox'
                commit: true, // This will add the transaction amount to the PayPal button

                payment: function () {
                    return paypalCheckoutInstance.createPayment({
                        flow: 'checkout', // Required
                        amount: 10.00, // Required
                        currency: 'USD', // Required
                        locale: 'en_US',
                        enableShippingAddress: true,
                        shippingAddressEditable: false,
                        shippingAddressOverride: {
                            recipientName: 'Scruff McGruff',
                            line1: '1234 Main St.',
                            line2: 'Unit 1',
                            city: 'Chicago',
                            countryCode: 'US',
                            postalCode: '60652',
                            state: 'IL',
                            phone: '123.456.7890'
                        }
                    });
                },
                onAuthorize: function (data, actions) {
                    return paypalCheckoutInstance.tokenizePayment(data)
                            .then(function (payload) {
                                // Submit `payload.nonce` to your server
                            });
                },
                onCancel: function (data) {
                    console.log('checkout.js payment cancelled', JSON.stringify(data, 0, 2));
                },
                onError: function (err) {
                    console.error('checkout.js error', err);
                }
            }, '#paypal-button').then(function () {
                // The PayPal button will be rendered in an html element with the id
                // `paypal-button`. This function will be called when the PayPal button
                // is set up and ready to be used.
            });
        });
    });
</script>
