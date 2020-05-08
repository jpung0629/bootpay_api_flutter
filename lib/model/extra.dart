class Extra {
  String startAt = '';
  String endAt = '';
  int expireMonth = 0;
  bool vbankResult = false;
  List<int> quotas;

  String appScheme = '';
  String appSchemeHost = '';

  String locale = 'ko';

  int popup = 0;
  String dispCashResult = 'Y';
  int escrow = 0;

  Extra();

  Extra.fromJson(Map<String, dynamic> json) {
    startAt = json["start_at"];
    endAt = json["end_at"];
    expireMonth = json["expire_month"];
    vbankResult = json["vbank_result"];
    quotas = json["quotas"];

    appScheme = json["app_scheme"];
    appSchemeHost = json["app_scheme_host"];

    locale = json["locale"];

    popup = json["popup"];
    dispCashResult = json["disp_cash_result"];
    escrow = json["escrow"];

//    onestore = json["onestore"];
  }

  Map<String, dynamic> toJson() => {
        "start_at": this.startAt,
        "end_at": this.endAt,
        "expire_month": this.expireMonth,
        "vbank_result": this.vbankResult,
        "quotas": this.quotas,
        "app_scheme": this.appScheme,
        "app_scheme_host": this.appSchemeHost,
        "locale": this.locale,
        "popup": this.popup,
        "disp_cash_result": this.dispCashResult,
        "escrow": this.escrow,
      };

  String getQuotas() {
    if (quotas == null || quotas.isEmpty) return '';
    String result = '';
    for (int quota in quotas) {
      if (result.length > 0) result += ',';
      result += quota.toString();
    }
    return result;
  }

  String toString() {
    return "{start_at: '${reVal(startAt)}', end_at: '${reVal(endAt)}', expire_month: ${reVal(expireMonth)}, vbank_result: ${reVal(vbankResult)}," +
        "quotas: '${getQuotas()}', app_scheme: '${reVal(appScheme)}', app_scheme_host: '${reVal(appSchemeHost)}', locale: '${reVal(locale)}'," +
        "popup: ${reVal(popup)}, disp_cash_result: '${reVal(dispCashResult)}', escrow: ${reVal(escrow)}}";
  }

  String reVal(dynamic value) {
    if (value is String) {
      if (value.isEmpty) {
        return '';
      }
      return value.replaceAll("\"", "'").replaceAll("'", "\\'") ?? '';
    } else {
      return value.toString() ?? 0;
    }
  }
}
