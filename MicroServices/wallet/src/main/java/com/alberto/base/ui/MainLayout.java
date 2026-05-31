package com.alberto.base.ui;

import com.alberto.security.SecurityService;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;


@Layout
@PermitAll
 public final class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;

        setPrimarySection(Section.NAVBAR);
        addToNavbar(createApplicationHeader());
        addToDrawer(createApplicationDrawer(), createApplicationFooter());

    }

    private Component createApplicationHeader() {
        // TODO Replace with real application logo and name
        var appLogo = new Avatar("MultiDigital Wallet");
        appLogo.setImage("images/wallet.png");
        appLogo.addClassName("app-logo");
        appLogo.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);

        var appName = new Span("MultiDigital Wallet");
        appName.addClassName("app-name");



        HorizontalLayout header = securityService.getAuthenticatedUser()
                .map(user -> {
                    Avatar avatarBasic = new Avatar();
                    avatarBasic.addThemeVariants(AvatarVariant.XSMALL);
                    avatarBasic.addClassName("user-avatar");

                    var username = new Span(user.getUsername());
                    username.addClassName("user-name");

                    var status = new Span();
                    status.setVisible(false);

                    var logout = getButton(user, status);
                    return new HorizontalLayout(new DrawerToggle(),
                            appLogo,
                            appName,
                            avatarBasic,
                            username,
                            logout,
                            status);
                })
                .orElseGet(() -> new HorizontalLayout(new DrawerToggle(), appLogo));

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(appName); // <4>
        header.setWidthFull();
        header.addClassNames(
                "app-navbar",
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setPadding(true);

        return header;
    }

    private @NonNull Button getButton(UserDetails user, Span status) {
        var dialog = new ConfirmDialog();
        dialog.setHeader("Log out?");
        dialog.setText("Are you sure you want to log out?");
        dialog.setCancelable(true);
        dialog.addCancelListener(event -> setStatus(status, "Canceled"));
        dialog.setConfirmText("Log out");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(event -> {
            setStatus(status, "Logged out");
            securityService.logout();
        });

        var logout = new Button(new Icon(VaadinIcon.SIGN_OUT), e -> dialog.open());
        logout.addClassName("logout-button");
        logout.setAriaLabel("Log out " + user.getUsername());
        return logout;
    }

    private void setStatus(Span status, String text) {
        status.setText(text);
        status.setVisible(true);
    }

    private Component createApplicationDrawer() {
        var scroller = new Scroller(createSideNav());
        scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);
        return scroller;
    }

    private Component createApplicationFooter() {
        var footer = new VerticalLayout(new Span("Made By Alberto Junior"));
        footer.setAlignItems(FlexComponent.Alignment.CENTER);
        footer.addClassName("app-footer");
        return footer;
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.setMinWidth(200, Unit.PIXELS);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            Component icon = null;
            if (menuEntry.icon().contains(".svg")) {
                icon = new SvgIcon(menuEntry.icon());
            } else {
                icon = new Icon(menuEntry.icon());
            }
            return new SideNavItem(menuEntry.title(), menuEntry.path(), icon);
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }
}
