terraform {
  backend "remote" {
    hostname = "app.terraform.io"
    organization = "ftsl"

    workspaces {
      name = "ftsl-nonprod"
    }
  }
}