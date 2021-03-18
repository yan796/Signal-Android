package org.thoughtcrime.securesms.keyvalue;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.preference.PreferenceDataStore;

import org.thoughtcrime.securesms.dependencies.ApplicationDependencies;
import org.thoughtcrime.securesms.util.SignalUncaughtExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple, encrypted key-value store.
 */
public final class SignalStore {

  private static final SignalStore INSTANCE = new SignalStore();

  private final KeyValueStore            store;
  private final KbsValues                kbsValues;
  private final RegistrationValues       registrationValues;
  private final PinValues                pinValues;
  private final RemoteConfigValues       remoteConfigValues;
  private final StorageServiceValues     storageServiceValues;
  private final UiHints                  uiHints;
  private final TooltipValues            tooltipValues;
  private final MiscellaneousValues      misc;
  private final InternalValues           internalValues;
  private final EmojiValues              emojiValues;
  private final SettingsValues           settingsValues;
  private final CertificateValues        certificateValues;
  private final PhoneNumberPrivacyValues phoneNumberPrivacyValues;
  private final OnboardingValues         onboardingValues;
  private final WallpaperValues          wallpaperValues;
  private final ProxyValues              proxyValues;

  private SignalStore() {
    this.store                    = new KeyValueStore(ApplicationDependencies.getApplication());
    this.kbsValues                = new KbsValues(store);
    this.registrationValues       = new RegistrationValues(store);
    this.pinValues                = new PinValues(store);
    this.remoteConfigValues       = new RemoteConfigValues(store);
    this.storageServiceValues     = new StorageServiceValues(store);
    this.uiHints                  = new UiHints(store);
    this.tooltipValues            = new TooltipValues(store);
    this.misc                     = new MiscellaneousValues(store);
    this.internalValues           = new InternalValues(store);
    this.emojiValues              = new EmojiValues(store);
    this.settingsValues           = new SettingsValues(store);
    this.certificateValues        = new CertificateValues(store);
    this.phoneNumberPrivacyValues = new PhoneNumberPrivacyValues(store);
    this.onboardingValues         = new OnboardingValues(store);
    this.wallpaperValues          = new WallpaperValues(store);
    this.proxyValues              = new ProxyValues(store);
  }

  public static void onFirstEverAppLaunch() {
    kbsValues().onFirstEverAppLaunch();
    registrationValues().onFirstEverAppLaunch();
    pinValues().onFirstEverAppLaunch();
    remoteConfigValues().onFirstEverAppLaunch();
    storageServiceValues().onFirstEverAppLaunch();
    uiHints().onFirstEverAppLaunch();
    tooltips().onFirstEverAppLaunch();
    misc().onFirstEverAppLaunch();
    internalValues().onFirstEverAppLaunch();
    emojiValues().onFirstEverAppLaunch();
    settings().onFirstEverAppLaunch();
    certificateValues().onFirstEverAppLaunch();
    phoneNumberPrivacy().onFirstEverAppLaunch();
    onboarding().onFirstEverAppLaunch();
    wallpaper().onFirstEverAppLaunch();
    proxy().onFirstEverAppLaunch();
  }

  public static List<String> getKeysToIncludeInBackup() {
    List<String> keys = new ArrayList<>();
    keys.addAll(kbsValues().getKeysToIncludeInBackup());
    keys.addAll(registrationValues().getKeysToIncludeInBackup());
    keys.addAll(pinValues().getKeysToIncludeInBackup());
    keys.addAll(remoteConfigValues().getKeysToIncludeInBackup());
    keys.addAll(storageServiceValues().getKeysToIncludeInBackup());
    keys.addAll(uiHints().getKeysToIncludeInBackup());
    keys.addAll(tooltips().getKeysToIncludeInBackup());
    keys.addAll(misc().getKeysToIncludeInBackup());
    keys.addAll(internalValues().getKeysToIncludeInBackup());
    keys.addAll(emojiValues().getKeysToIncludeInBackup());
    keys.addAll(settings().getKeysToIncludeInBackup());
    keys.addAll(certificateValues().getKeysToIncludeInBackup());
    keys.addAll(phoneNumberPrivacy().getKeysToIncludeInBackup());
    keys.addAll(onboarding().getKeysToIncludeInBackup());
    keys.addAll(wallpaper().getKeysToIncludeInBackup());
    keys.addAll(proxy().getKeysToIncludeInBackup());
    return keys;
  }

  /**
   * Forces the store to re-fetch all of it's data from the database.
   * Should only be used for testing!
   */
  @VisibleForTesting
  public static void resetCache() {
    INSTANCE.store.resetCache();
  }

  public static @NonNull KbsValues kbsValues() {
    return INSTANCE.kbsValues;
  }

  public static @NonNull RegistrationValues registrationValues() {
    return INSTANCE.registrationValues;
  }

  public static @NonNull PinValues pinValues() {
    return INSTANCE.pinValues;
  }

  public static @NonNull RemoteConfigValues remoteConfigValues() {
    return INSTANCE.remoteConfigValues;
  }

  public static @NonNull StorageServiceValues storageServiceValues() {
    return INSTANCE.storageServiceValues;
  }

  public static @NonNull UiHints uiHints() {
    return INSTANCE.uiHints;
  }

  public static @NonNull TooltipValues tooltips() {
    return INSTANCE.tooltipValues;
  }

  public static @NonNull MiscellaneousValues misc() {
    return INSTANCE.misc;
  }

  public static @NonNull InternalValues internalValues() {
    return INSTANCE.internalValues;
  }

  public static @NonNull EmojiValues emojiValues() {
    return INSTANCE.emojiValues;
  }

  public static @NonNull SettingsValues settings() {
    return INSTANCE.settingsValues;
  }

  public static @NonNull CertificateValues certificateValues() {
    return INSTANCE.certificateValues;
  }

  public static @NonNull PhoneNumberPrivacyValues phoneNumberPrivacy() {
    return INSTANCE.phoneNumberPrivacyValues;
  }

  public static @NonNull OnboardingValues onboarding() {
    return INSTANCE.onboardingValues;
  }

  public static @NonNull WallpaperValues wallpaper() {
    return INSTANCE.wallpaperValues;
  }

  public static @NonNull ProxyValues proxy() {
    return INSTANCE.proxyValues;
  }

  public static @NonNull GroupsV2AuthorizationSignalStoreCache groupsV2AuthorizationCache() {
    return new GroupsV2AuthorizationSignalStoreCache(getStore());
  }

  public static @NonNull PreferenceDataStore getPreferenceDataStore() {
    return new SignalPreferenceDataStore(getStore());
  }

  /**
   * Ensures any pending writes are finished. Only intended to be called by
   * {@link SignalUncaughtExceptionHandler}.
   */
  public static void blockUntilAllWritesFinished() {
    getStore().blockUntilAllWritesFinished();
  }

  private static @NonNull KeyValueStore getStore() {
    return INSTANCE.store;
  }
}
