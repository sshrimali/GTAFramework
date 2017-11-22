package utils.framework;

import java.util.HashMap;

import static utils.framework.GlobalElementRepository.NAVIGATION.globalElementRepository;

/**
 * Created by sshrimali on 1/25/17.
 */
public class GlobalElementRepository {
    public String GetButtonLocatorByLabel(String label)
    {
        String locator = "//button[text()='" + label +"']";
        return locator;
    }
    public String GetSpanByMatchingText(String label)
    {
        String locator = "//span[contains(text(),'" + label +"')]";
        return locator;
    }
    public String GetLinkByText(String label)
    {
        String locator = "//a[contains(text(),'" + label +"')]";
        return locator;
    }
    public static class NAVIGATION
    {
        static GlobalElementRepository globalElementRepository = new GlobalElementRepository();
        public static String moreLinkSignedOut = "div.more-item > div.header";
        public static String moreLinkSignedIn = "div.user-item > div.header";
        public static String helpLink = globalElementRepository.GetLinkByText("Help");
        public static String createAccountLink = globalElementRepository.GetLinkByText("Create an account");
        public static String signOutLink = globalElementRepository.GetLinkByText("Sign out");
        public static String signInLink = globalElementRepository.GetLinkByText("Sign in");
        public static String closeIcon = ".icon--cancel";
        public static String booksLink = ".link-books";
        public static String rentBuyBooksLink = ".link-rent-buy";
        public static String sellBooksLink = ".link-sell";
        public static String studyLink = ".link-study";
        public static String qnaLink = ".link-expert-qa";
        public static String tbsLink = ".link-textbook-solutions";
        public static String scholarshipsLink = ".link-scholarships";
        public static String internshipsLink = ".link-internships";
        public static String tutorsLink = ".link-tutors";
        public static String testprepLink = ".link-test-prep,.icon--TestPrep";
    }
    public static class CART
    {
        public static String shoppingCart = ".chg-cart";
        public static String buttonCheckout = ".btn-checkout";
    }
    public static class CHECKOUT
    {
        public static String buttonContinue = ".payment-continue";
        public static String placeOrder = ".test-place-order";
    }
        public static String buttonOK = "//button[@title='OK']";
        public static String buttonSave = ".save-info-button";

    public static class PDP {
        public static String addToCartButton = globalElementRepository.GetButtonLocatorByLabel("Add to cart");;
        public static String priceBoxContainer = ".pricebox-col";
    }

    public static class HomePage {
        public static String hamburgerIcon = ".chgg-menu-icon";
    }
}
