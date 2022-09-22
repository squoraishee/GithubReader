package com.sq.githubreader.di

import com.sq.githubreader.network.api.GithubApi
import com.sq.githubreader.network.repository.GithubRepository
import com.sq.githubreader.network.repository.OrgCache
import com.sq.githubreader.network.usecase.GetOrganizations
import com.sq.githubreader.network.usecase.GetRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // ideally this wod come from an environment variable
    // or injected into a string from the CI, but since this is
    // a weakly scoped and short lived token, for this demo app,
    // this is fine
    @Named("api_key")
    @Provides
    fun providesApiKey() :String {
        return "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"
    }

    @Provides
    @Singleton
    fun providesHTTPClient(
        @Named("api_key") apiKey: String
    ) : OkHttpClient {
        val enableLog = true
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (enableLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(Interceptor { chain ->
                val ongoing: Request.Builder = chain.request().newBuilder()
                ongoing.addHeader("Accept", "application/vnd.github+json")
                ongoing.addHeader("Authorization", "Bearer $apiKey")
                chain.proceed(ongoing.build())
            })
            .connectTimeout(10000, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesGithubApi(okHttpClient: OkHttpClient) : GithubApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(GithubApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)

    @Provides
    @Singleton
    fun providesGetRepos(githubApi: GithubApi) : GetRepos {
        return GetRepos(githubApi)
    }

    @Provides
    @Singleton
    fun providesGetOrgs(githubApi: GithubApi) : GetOrganizations {
        return GetOrganizations(githubApi)
    }

    @Provides
    @Singleton
    fun providesOrgCache() : OrgCache {
        return OrgCache()
    }

    @Provides
    @Singleton
    fun providesGithubRepository(
        getRepos: GetRepos,
        getOrganizations: GetOrganizations,
        orgCache: OrgCache
    ) : GithubRepository {
        return GithubRepository(getRepos, getOrganizations, orgCache)
    }
}