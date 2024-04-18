export interface LoginDto {
  usernameOrEmail: string;
  password: string;
}

export interface SignUpDto {
  name?: string;
  username?: string;
  email?: string;
  password?: string;
}
