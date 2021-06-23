package com.example.android.dhis2explorer.ui.login;

import androidx.annotation.Nullable;

class LoginFormState {
    @Nullable
    private final Integer serverUrlError;
    @Nullable
    private final Integer usernameError;
    @Nullable
    private final Integer passwordError;
    private final boolean isDataValid;

    LoginFormState(@Nullable Integer serverUrlError, @Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.serverUrlError = serverUrlError;
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.serverUrlError = null;
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getServerUrlError() {
        return serverUrlError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
