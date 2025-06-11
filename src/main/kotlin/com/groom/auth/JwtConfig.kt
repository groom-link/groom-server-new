package com.groom.auth

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
class JwtConfig {
    @Bean
    fun jwtDecoder(rsaKey: RSAKey): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
            .build()
    }

    @Bean
    fun jwtEncoder(jwkSource: JWKSource<SecurityContext?>): JwtEncoder {
        return NimbusJwtEncoder(jwkSource)
    }


    @Bean
    fun jwkSource(rsaKey: RSAKey?): JWKSource<SecurityContext> {
        return ImmutableJWKSet(JWKSet(rsaKey))
    }

    @Bean
    fun rsaKey(keyPair: KeyPair): RSAKey {
        return RSAKey.Builder(keyPair.public as RSAPublicKey).privateKey(
            keyPair.private as RSAPrivateKey
        )
            .keyID(
                UUID.randomUUID()
                    .toString()
            )
            .build()
    }

    @Bean
    fun keyPair(@Value("\${spring.application.name:key}") path: String): KeyPair {
        val privateKeyFile = File(path)
        val publicKeyPath = "$path.pub"
        val publicKeyFile = File(publicKeyPath)
        if (privateKeyFile.exists() && publicKeyFile.exists()) {
            return readKeyPairFromFile(path, publicKeyPath)
        }
        val keyPair = generateNewKeyPair()
        saveFileKeyPair(keyPair, path, publicKeyPath)
        return keyPair
    }

    private fun generateNewKeyPair(): KeyPair {
        val rsaKeyGenerator = RSAKeyGenerator(RSAKeyGenerator.MIN_KEY_SIZE_BITS)
        val generated: RSAKey = rsaKeyGenerator.generate()
        return KeyPair(generated.toPublicKey(), generated.toPrivateKey())
    }

    private fun readKeyPairFromFile(path: String, publicKeyPath: String): KeyPair {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val privateKey: PrivateKey = factory.generatePrivate(
            PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(path)))
        )
        val publicKey: PublicKey = factory.generatePublic(
            X509EncodedKeySpec(Files.readAllBytes(Paths.get(publicKeyPath)))
        )
        return KeyPair(publicKey, privateKey)
    }

    private fun saveFileKeyPair(keyPair: KeyPair, path: String, publicKeyPath: String) {
        val privateKey = keyPair.private
        val publicKey = keyPair.public
        FileOutputStream(path).use { fos ->
            fos.write(privateKey.encoded)
        }
        FileOutputStream(publicKeyPath).use { fos ->
            fos.write(publicKey.encoded)
        }
    }
}