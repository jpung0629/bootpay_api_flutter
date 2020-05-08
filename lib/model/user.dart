class User {
  String id = '';
  String username = '';
  String email = '';
  int gender = 0;

  String birth = '';
  String phone = '';
  String area = '';

  User();

  User.fromJson(Map<String, dynamic> json) {
    id = json["id"];
    username = json["username"];
    email = json["email"];
    gender = json["gender"];

    birth = json["birth"];
    phone = json["phone"];
    area = json["area"];
  }

  Map<String, dynamic> toJson() => {
        "id": this.id,
        "username": this.username,
        "email": this.email,
        "gender": this.gender,
        "birth": this.birth,
        "phone": this.phone,
        "area": this.area,
      };

  String toString() {
    return "{id: '${reVal(id)}', username: '${reVal(username)}', email: '${reVal(email)}', gender: ${reVal(gender)}, birth: '${reVal(birth)}', phone: '${reVal(phone)}', area: '${reVal(area)}'}";
  }

  String reVal(dynamic value) {
    if (value is String) {
      if (value.isEmpty) {
        return '';
      }
      return value.replaceAll("\"", "'").replaceAll("'", "\\'");
    } else {
      return value;
    }
  }
}
