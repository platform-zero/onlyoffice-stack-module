from pathlib import Path
import unittest
ROOT=Path(__file__).resolve().parents[1]
class NativeStorageRoute(unittest.TestCase):
 def test_owned_callbacks_preserve_public_hostnames_and_tls(self):
  text=(ROOT/'stack.runtime.yaml').read_text()
  for host in('seafile','files-native'):self.assertIn('"'+host+'.${DOMAIN}:host-gateway"',text)
  for name in('NODE_EXTRA_CA_CERTS','SSL_CERT_FILE','CURL_CA_BUNDLE'):self.assertIn(name+': "/ca/caddy-ca.crt"',text)
  self.assertIn('"caddy_ca:/ca:ro"',text);self.assertNotIn('NODE_TLS_REJECT_UNAUTHORIZED',text);self.assertNotIn('insecure',text.lower())
if __name__=='__main__':unittest.main()
