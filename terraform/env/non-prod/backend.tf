terraform {
  backend "local" {
    path = "./env/non-prod/state/terraform.tfstate"
  }
}