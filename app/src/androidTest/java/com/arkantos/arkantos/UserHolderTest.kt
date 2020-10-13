package com.arkantos.arkantos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkantos.arkantos.helpers.UserHolder
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.firebase.ui.auth.data.model.User
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserHolderTest {
    @Test
    fun testSetUser() {
        val user = UserNetworkModel("a", "b", "c", "d", "e")
        UserHolder.setUser(user)
        val userFromUserHolder = UserHolder.getUser()
        Assert.assertEquals(user.token, userFromUserHolder.token)
        Assert.assertEquals(user.name, userFromUserHolder.name)
        Assert.assertEquals(user.email, userFromUserHolder.email)
    }
}